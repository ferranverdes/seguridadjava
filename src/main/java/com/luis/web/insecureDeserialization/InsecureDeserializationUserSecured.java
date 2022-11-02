package com.luis.web.insecureDeserialization;

import com.luis.web.sqli.User;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.apache.logging.log4j.LogManager;


@WebServlet(name = "InsecureDeserializationUserSecured", urlPatterns = {"/InsecureDeserializationUserSecured"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024,
        maxRequestSize = 1024 * 1024
)
public class InsecureDeserializationUserSecured extends HttpServlet {

    public static final String UploadDirectory = "/var/webapp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        if(fileName == null || fileName.equals("")){
            request.getRequestDispatcher("/deserialization/deserialization.jsp").forward(request, response);
        } else {
            //Sirve el fichero
            File file = new File(UploadDirectory + File.separator + fileName);
            if(!file.exists()){
                    throw new ServletException("File doesn't exists on server.");
            }
            System.out.println("File location on server::" + file.getAbsolutePath());
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());
            response.setContentType(mimeType != null? mimeType:"application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                    os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
            System.out.println("File downloaded at client successfully");
        }
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User();
        
        String action = request.getParameter("action");
        
        if (action.equals("Guardar")) {
            if (request.getParameter("name") != null) {
                user.setName(request.getParameter("name"));
            }
            
            
            if (request.getPart("avatar").getSubmittedFileName() != null) {
                user.setAvatar(request.getPart("avatar").getSubmittedFileName());
            
                //Vulnerable file upload. Path traversal, No se comprueba tipo mime, extensión...
                Part filePart = request.getPart("avatar");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                String savePath = UploadDirectory + File.separator + fileName;
                File newFile = new File(savePath);
                InputStream fileContent = filePart.getInputStream();
                Files.copy(fileContent, newFile.toPath());
                request.setAttribute("filename", fileName);
            }
            try {
                ByteArrayOutputStream bo  = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo);
                oo.writeObject(user);
                String serializedUser = Base64.getEncoder().encodeToString(bo.toByteArray());
                Cookie cookie = new Cookie("user", serializedUser);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                response.addCookie(cookie);
            } catch (IOException e) {
                System.out.println(e);
            }
            
            request.getRequestDispatcher("/deserialization/deserialization.jsp").forward(request, response);
        }
        
        if (action.equals("Borrar")) {
            if (request.getCookies().length > 0) {
                String data = getCookie(request.getCookies(), "user");
                // Insecure Deserialization
                try {                
                    user = deserializationSecured(data);
                } catch (ClassNotFoundException ex) {
                    LogManager.getLogger(InsecureDeserializationUserSecured.class).debug("Excepcion");
                }
                //user = deserializationSecured(data);
                //Arbitrary delete
                File file = new File(UploadDirectory + File.separator + user.getAvatar());
                file.delete();
               
            }
            response.sendRedirect("/seguridadjava/InsecureDeserializationUser");
        }
                   
    }
    
    User deserializationSecured(String data) throws IOException, ClassNotFoundException {
        User user = null;
        byte [] serializedUser = Base64.getDecoder().decode(data);
        ByteArrayInputStream bi = new ByteArrayInputStream(serializedUser);
        //Uso librería commons io para aceptar solo objetos de la clase User en la deserialización
        ValidatingObjectInputStream in = new ValidatingObjectInputStream(bi);
        in.accept(User.class);
        user = (User) in.readObject();
        return user;
    }
    
    String getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
}
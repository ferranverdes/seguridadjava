<%@page contentType="text/html" pageEncoding="UTF-8"%>
            
        <!-- Sidebar  -->
        <nav id="sidebar">
            <div class="sidebar-header">
                <h3><a href="/seguridadjava/">Laboratorio</a></h3>
            </div>

            <ul class="list-unstyled components">
                <p>Java EE</p>
                <li class="active">
                    <a href="#introSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Intro</a>
                    <ul class="collapse list-unstyled" id="introSubmenu">
                        <li>
                            <a href="/seguridadjava/intro/IntegerOverflow.jsp">Integer overflow</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/intro/IntegerOverflowSolved.jsp">Integer overflow Solucionado</a>
                        </li>
                    </ul>
                </li>                
                <li>
                    <a href="#reflejadoSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">XSS Reflejado</a>
                    <ul class="collapse list-unstyled" id="reflejadoSubmenu">
                        <li>
                            <a href="/seguridadjava/xss/reflected/scriptlet.jsp">Scriptlet</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/expression.jsp">Expression</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/reflected_servlet?color=red">Servlet</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/expression_language.jsp">Expression Language</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/scriptlet_1.jsp">Protección errónea 1</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/scriptlet_2.jsp">Protección errónea 2</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/scriptlet_3.jsp">Protección errónea 3</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/scriptlet_4.jsp">Protección errónea 4</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/reflected/login.jsp">Login</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#persistedSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">XSS Persistente</a>
                    <ul class="collapse list-unstyled" id="persistedSubmenu">
                        <li>
                            <a href="/seguridadjava/xss/persistent">XSS JSP Persisted</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/persistent1">Protección insegura JSP Persisted en url autor</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#domSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">XSS DOM</a>
                    <ul class="collapse list-unstyled" id="domSubmenu">
                        <li>
                            <a href="/seguridadjava/xss/dom/dom.jsp">DOM select</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/dom/dom1.jsp?name=PruebaXSS">DOM error al codificar en javascript</a>
                        </li>
                    </ul>
                </li>
                <li><a href="/seguridadjava/contextpath-manipulation.jsp">XSS ContextPath URL Manipulation</a></li>
                <li>
                    <a href="#notVulnSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">XSS No vulnerables</a>
                    <ul class="collapse list-unstyled" id="notVulnSubmenu">
                        <li>
                            <a href="/seguridadjava/xss/secured/jstl.jsp">JSTL</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/xss/secured/jsf.xhtml">JSF</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#csrfSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">CSRF</a>
                    <ul class="collapse list-unstyled" id="csrfSubmenu">
                        <li>
                            <a href="/seguridadjava/csrftest">Formulario</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/csrf/csrf_action.xhtml">JSF anticsrf</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#deserSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Deserialización insegura</a>
                    <ul class="collapse list-unstyled" id="deserSubmenu">
                        <li>
                            <a href="/seguridadjava/InsecureDeserialization">Deserialización con parámetro</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/InsecureDeserializationUser">Deserialización con cookie</a>
                        </li>
                         <li>
                            <a href="/seguridadjava/InsecureDeserializationUserSecured">Deserialización segura commons io con cookie</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#ssrfSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">SSRF</a>
                    <ul class="collapse list-unstyled" id="ssrfSubmenu">
                        <li>
                            <a href="/seguridadjava/SSRF1">Proxy SSRF OpenConnection</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SSRF2">Proxy SSRF Apache HttpClient</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SSRF3">Proxy SSRF Apache HttpClient Blacklisted</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SSRF4">Proxy SSRF Apache HttpClient Whitelisted</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SSRF5">Proxy SSRF Safe</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/secret">Localhost only access Secret</a>
                        </li>
                    </ul>
                </li>
                 <li>
                    <a href="#sqliSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">SQLi</a>
                    <ul class="collapse list-unstyled" id="sqliSubmenu">
                        <li>
                            <a href="/seguridadjava/SQLI1">SQL Injection 1</a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SQLI2">Filter SQL Injection 2 </a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SQLI3">Secured? SQL Injection 3 </a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SQLI4">Blind SQL Injection 4 </a>
                        </li>
                        <li>
                            <a href="/seguridadjava/SQLI5">SQL Injection Insert 5 </a>
                        </li>
                        <li>
                            <a href="/seguridadjava/api/vuser">NoSQL Injection </a>
                        </li>
                        <li>
                            <a href="/seguridadjava/api/user">Solved NoSQL Injection </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </nav>

        
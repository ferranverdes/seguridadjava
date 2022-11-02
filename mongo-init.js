db.createUser(
        {
            user: "devroot",
            pwd: "password",
            roles: [
                {
                    role: "readWrite",
                    db: "bonarea"
                }
            ]
        }
);
db = new Mongo().getDB("bonarea");
db.createCollection('users', { capped: false });
db.users.insert({name:"admin", username:"admin", password:"admin"})
db.users.insert({name:"test", username:"test", password:"super"})

from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dotenv import load_dotenv
from urllib.parse import quote  
import os
from __main__ import app

# create the extension
db = SQLAlchemy()
# create the app
#app = Flask(__name__)


load_dotenv()
db_userName = os.getenv("DB_USER")
db_pw = os.getenv("DB_PW")
db_ip = os.getenv("DB_IP")
db_port = os.getenv("DB_PORT")
db_schema = os.getenv("DB_SCHEMA")

app.config["SQLALCHEMY_DATABASE_URI"] = f'mysql+pymysql://{db_userName}:%s@{db_ip}:{db_port}/{db_schema}' % quote(db_pw)
# initialize the app with the extension
db.init_app(app)

class User(db.Model):
    __tablename__ = 'Users'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    id = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(50), nullable=False)
    name = db.Column(db.String(50), nullable=False)
    sex = db.Column(db.Integer, nullable=False)
    email = db.Column(db.String(50), nullable=False)
    

    def __init__(self, id, password, name, sex, email):
        self.id = id
        self.password = password
        self.name = name
        self.sex = sex
        self.email = email


    def __str__(self):
        return f" uid: {self.uid}\n id: {self.id}\n name: {self.name}\n password: {self.password}\n sex: {self.sex}\n email: {self.email}\n"
    
    def toDict_without_password(self):
        userDict = {
            "uid" : self.uid,
            "id" : self.id,
            "name" : self.name,
            "sex" : self.sex,
            "email" : self.email
        }
        return userDict

    

def create_table():
    with app.app_context():
        db.create_all()

def print_all_users_list():
    with app.app_context():
        users = db.session.query(User).all()
        for user in users:
            print(user)

def select_users():
    with app.app_context():
        users = db.session.query(User).all()
    return users


def select_user_with_uid(uid):
    with app.app_context():
        user = db.session.query(User).filter(User.uid == uid).first()
    return user

def select_user_with_id(id):
    with app.app_context():
        user = db.session.query(User).filter(User.id == id).first()
    return user

def select_user_with_name(name):
    with app.app_context():
        user = db.session.query(User).filter(User.name == name).first()
    return user


def select_user_with_email(email):
    with app.app_context():
        user = db.session.query(User).filter(User.email == email).first()
    return user

def insert_user(user):
    try:
        with app.app_context():
            db.session.add(user)
            db.session.commit()
    except Exception as e:
        print(e.args)
        raise e

def delete_user_with_uid(uid):
    try:
        with app.app_context():
            user = db.session.query(User).filter(User.uid == uid).first()
            if user == None:
                return None

            db.session.delete(user)
            db.session.commit()
    except Exception as e:
        print(e.args)

def update_user_with_uid(uid, userModi):
    # id 중복 같은건 사용할 떄 걸러낼 것

    try:
        with app.app_context():
            user = db.session.query(User).filter(User.uid == uid).first()
            if user == None:
                return None
            
            user.id = userModi.id
            user.password = userModi.password
            user.name = userModi.name
            user.sex = userModi.sex
            user.email = userModi.email
            
            db.session.commit()
    except Exception as e:
        print(e.args)




if __name__ == "__main__":
    
    print_all_users_list()
    ...
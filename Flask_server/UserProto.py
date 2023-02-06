from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dotenv import load_dotenv
import os

# create the extension
db = SQLAlchemy()
# create the app
app = Flask(__name__)

load_dotenv()
db_userName = os.getenv("DB_USER")
db_pw = os.getenv("DB_PW")
db_ip = os.getenv("DB_IP")
db_port = os.getenv("DB_PORT")
db_schema = os.getenv("DB_SCHEMA")

app.config["SQLALCHEMY_DATABASE_URI"] = f"mariadb+pymysql://{db_userName}:{db_pw}@{db_ip}:{db_port}/{db_schema}"
# initialize the app with the extension
db.init_app(app)


class UserProto(db.Model):
    __tablename__ = 'users'
    user_seq = db.Column(db.Integer, primary_key=True)
    user_name = db.Column(db.String(100), unique=True, nullable=False)
    user_password = db.Column(db.String(100), nullable=False)
    user_email = db.Column(db.String(100), nullable=False, unique=True)
    user_profile_image_url = db.Column(db.String(500), default = "default.png")
    user_register_date = db.Column(db.DateTime(timezone=True), default=datetime.now())

    def __init__(self, name, pw, email, profile_image_url = None):
        self.user_name = name
        self.user_password = pw
        self.user_email = email
        if profile_image_url is not None:
            self.user_profile_image_url = profile_image_url


    def __str__(self):
        return "user_seq:" + str(self.user_seq) + "\t user_name:" + self.user_name + "\t user_email:" + self.user_email 


def create_table():
    with app.app_context():
        db.create_all()


def print_all_users_list():
    with app.app_context():
        users = db.session.query(UserProto).all()
        for user in users:
            print(user)

def select_users():
    with app.app_context():
        users = db.session.query(UserProto).all()

    return users



def select_user_with_id(id):
    with app.app_context():
        user = db.session.query(UserProto).filter(UserProto.user_seq == id).first()

    return user

def select_user_with_name(name):
    with app.app_context():
        user = db.session.query(UserProto).filter(UserProto.user_name == name).first()

    return user

def select_user_with_email(email):
    with app.app_context():
        user = db.session.query(UserProto).filter(UserProto.user_email == email).first()

    return user

def insert_user(user):
    try:
        with app.app_context():
            db.session.add(user)
            db.session.commit()
    except Exception as e:
        print(e.args)


def delete_user(user):
    pass


def update_user(user):
    pass


if __name__ == "__main__":
    create_table()
    # user = User("flaskName22", "123123", "asddf@1235", "image.png")
    # insert_user(user)
    # print_all_users_list()
    select_user_with_id(1)
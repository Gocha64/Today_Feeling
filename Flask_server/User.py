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

app.config["SQLALCHEMY_DATABASE_URI"] = f'mysql+pymysql://{db_userName}:{db_pw}@{db_ip}:{db_port}/{db_schema}'
# initialize the app with the extension
db.init_app(app)

class User(db.Model):
    __tablename__ = 'Users'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    id = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(50), nullable=False)
    name = db.Column(db.String(50), nullable=False)
    sex = db.Column(db.Integer, nullable=False)
    

    def __init__(self, id, password, name, sex):
        self.id = id
        self.password = password
        self.name = name
        self.sex = sex


    def __str__(self):
        return f" uid: {self.uid}\n id: {self.id}\n name: {self.name}\n sex: {self.sex}\n"
    

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

def insert_user(user):
    try:
        with app.app_context():
            db.session.add(user)
            db.session.commit()
    except Exception as e:
        print(e.args)


if __name__ == "__main__":
    #create_table()
    user = User("Test", "1234", "insertTestName", 1)
    insert_user(user)
    #print_all_users_list()
    #user1 = select_user_with_uid(1)
    #print(user1)
    print_all_users_list()
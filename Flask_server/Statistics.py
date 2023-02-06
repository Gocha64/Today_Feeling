from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dotenv import load_dotenv
import os
import User
import Playlist

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


class Statistics(db.Model):
    __tablename__ = 'statistics'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    userId = db.Column(db.Integer, db.ForeignKey(User.User.uid), unique=True, nullable=False )
    datetime = db.Column(db.DateTime(timezone=True), nullable=False)
    songID = db.Column(db.Integer, db.ForeignKey(Playlist.Playlist.uid), unique=True, nullable=False )
    emotion = db.Column(db.Integer, nullable=False)
    

    def __init__(self, userId, songID, emotion):
        self.userId = userId
        self.songID = songID
        self.emotion = emotion

    def __str__(self):
        return f" uid: {self.uid}\n userId: {self.userId}\n datetime: {self.datetime}\n songID: {self.songID}\n emotion: {self.emotion}"
    

def create_table():
    with app.app_context():
        db.create_all()

def insert_statistics(statistics):
    try:
        with app.app_context():
            db.session.add(statistics)
            db.session.commit()
    except Exception as e:
        print(e.args)

def select_statistics():
    with app.app_context():
        users = db.session.query(Statistics).all()
    return users

def select_statistics_with_uid(uid):
    with app.app_context():
        statistics = db.session.query(Statistics).filter(Statistics.uid == uid).first()
    return statistics



if __name__ == "__main__":
    #create_table()
    #user = User("flaskName22", "123123", "name", 1)
    #insert_user(user)
    #print_all_users_list()
    statistics = Statistics(1,1,2)
    insert_statistics(statistics)
    statistics1 = select_user_with_uid(1)
    print(statistics1)
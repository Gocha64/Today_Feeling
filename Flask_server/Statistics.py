from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime, timedelta
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

app.config["SQLALCHEMY_DATABASE_URI"] = f"mysql+pymysql://{db_userName}:{db_pw}@{db_ip}:{db_port}/{db_schema}"
# initialize the app with the extension
db.init_app(app)


class Statistics(db.Model):
    __tablename__ = 'Statistics'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    userUID = db.Column(db.Integer, db.ForeignKey(User.User.uid), unique=True, nullable=False )
    datetime = db.Column(db.DateTime(timezone=True), nullable=False)
    songUID = db.Column(db.Integer, db.ForeignKey(Playlist.Playlist.uid), unique=True, nullable=False )
    emotion = db.Column(db.Integer, nullable=False)
    

    def __init__(self, userUID, songUID, emotion):
        self.userUID = userUID
        self.songUID = songUID
        self.datetime = datetime.now()
        self.emotion = emotion

    def __str__(self):
        return f" uid: {self.uid}\n userUID: {self.userUID}\n datetime: {self.datetime}\n songUID: {self.songUID}\n emotion: {self.emotion}\n"
    

def create_table():
    with app.app_context():
        db.create_all()


# userUID와 songUID는 각 테이블에 존재해야함
def insert_statistics(statistics):
    try:
        with app.app_context():
            db.session.add(statistics)
            db.session.commit()
    except Exception as e:
        print(e.args)

def print_all_statistics_list():
    with app.app_context():
        statistics = db.session.query(Statistics).\
            all()
        for s in statistics:
            print(s)

def select_statistics():
    with app.app_context():
        statistics = db.session.query(Statistics).all()
    return statistics

def select_statistics_with_userUID(userUID):
    with app.app_context():
        statistics = db.session.query(Statistics).filter(Statistics.userUID == userUID).all()
    return statistics


# 최근 24시간의 데이터 리스트 반환, default는 서버시간의 날짜
# datetime의 date형식으로 
def select_statistics_with_userUID_Day(userUID , curDate = datetime.now()):
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.datetime >= curDate - timedelta(days = 1)).all()
    return statistics


# 최근 1주일의 데이터 리스트 반환, default는 서버시간의 날짜
# datetime의 date형식으로 
def select_statistics_with_userUID_Week(userUID , curDate = datetime.now()):
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.datetime >= curDate - timedelta(weeks = 1)).all()
    return statistics

# 최근 1개월(30일)의 데이터 리스트 반환, default는 서버시간의 날짜
# datetime의 date형식으로 
def select_statistics_with_userUID_Month(userUID , curDate = datetime.now()):
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.datetime >= curDate - timedelta(days = 30)).all()
    return statistics


def select_statistics_with_uid(uid):
    with app.app_context():
        statistics = db.session.query(Statistics).filter(Statistics.uid == uid).first()
    return statistics


def delete_statistics_with_uid(uid):
    try:
        with app.app_context():
            statistics = db.session.query(Statistics).filter(Statistics.uid == uid).first()
            db.session.delete(statistics)
            db.session.commit()
    except Exception as e:
        print(e.args)



if __name__ == "__main__":
    # create_table()
    # sta = Statistics(15, 3, 3)
    # insert_statistics(sta)

    # delete_statistics_with_uid(8)
    # print_all_statistics_list()

    # statistics = Statistics(1,1,2)
    # insert_statistics(statistics)

    # staList = select_statistics()
    # for s in staList:

    #     print(s.datetime.date())


    statistics1 = select_statistics_with_userUID_Month(15)
    for s in statistics1:
        print(s)
    
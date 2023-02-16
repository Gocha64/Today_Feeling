from datetime import datetime, timedelta
from models.Statistics import Statistics
from extensions import db
from flask import current_app as app


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


def select_statistics_with_userUID_Day(userUID , curDate = datetime.now()):
    """
    최근 24시간의 데이터 리스트 반환, 

    ### Parameters
    1. userUID : int
        - 사용자의 uid
    2. curDate : datatime, (default: datetime.now())
        - 검색할 기준 날짜
    """
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.dateTime >= curDate - timedelta(days = 1)).\
            filter(Statistics.dateTime <= curDate).all()
    return statistics


def select_statistics_with_userUID_Week(userUID , curDate = datetime.now()):
    """
    최근 1주일의 데이터 리스트 반환, 

    ### Parameters
    1. userUID : int
        - 사용자의 uid
    2. curDate : datatime, (default: datetime.now())
        - 검색할 기준 날짜
    """
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.dateTime >= curDate - timedelta(weeks = 1)).\
            filter(Statistics.dateTime <= curDate).all()
    return statistics


def select_statistics_with_userUID_Month(userUID , curDate = datetime.now()):
    """
    최근 1개월(30일)의 데이터 리스트 반환, 

    ### Parameters
    1. userUID : int
        - 사용자의 uid
    2. curDate : datatime, (default: datetime.now())
        - 검색할 기준 날짜
    """
    with app.app_context():
        statistics = db.session.query(Statistics).\
            filter(Statistics.userUID == userUID).\
            filter(Statistics.dateTime >= curDate - timedelta(days = 30)).\
            filter(Statistics.dateTime <= curDate).all()
    return statistics


def select_statistics_with_uid(uid):
    with app.app_context():
        statistics = db.session.query(Statistics).filter(Statistics.uid == uid).first()
    return statistics


def delete_statistics_with_uid( uid):
    try:
        with app.app_context():
            statistics = db.session.query(Statistics).filter(Statistics.uid == uid).first()
            db.session.delete(statistics)
            db.session.commit()
    except Exception as e:
        print(e.args)


# 테스트용 더미 데이터 생성
def insert_dummy_Data():

    for i in range(40):
        time = datetime.now() - timedelta(days=i)
        sta = Statistics(2, 1, 2, time)
        insert_statistics(sta)


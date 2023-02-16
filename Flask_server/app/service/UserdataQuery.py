from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from models.Userdata import Userdata
from extensions import db
from flask import current_app as app

default_value = '0000000001'
    

def print_all_userdatas_list():
    with app.app_context():
        userdatas = db.session.query(Userdata).all()
        for userdata in userdatas:
            print(userdata)

def select_userdatas():
    with app.app_context():
        userdatas = db.session.query(Userdata).all()
    return userdatas

def insert_userdata(userdata):
    try:
        with app.app_context():
            db.session.add(userdata)
            db.session.commit()
    except Exception as e:
        print(e.args)
        raise e
    
def select_userdata_with_uid( uid):
    with app.app_context():
        userdata = db.session.query(Userdata).filter(Userdata.userUid == uid).first()
    return userdata

def update_userdata_with_uid(uid, userdataModi):

    with app.app_context():
        userdata = db.session.query(Userdata).filter(Userdata.userUid == uid).first()
        if userdata == None:
            return None
        
        userdata.anger = userdataModi.anger
        userdata.fear = userdataModi.fear
        userdata.happiness = userdataModi.happiness
        userdata.sadness = userdataModi.sadness
        userdata.surprise = userdataModi.surprise

        db.session.commit()



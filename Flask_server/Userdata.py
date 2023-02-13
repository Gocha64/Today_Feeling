from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dotenv import load_dotenv
from urllib.parse import quote  
import os
import User

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

app.config["SQLALCHEMY_DATABASE_URI"] = f'mysql+pymysql://{db_userName}:%s@{db_ip}:{db_port}/{db_schema}' % quote(db_pw)
# initialize the app with the extension
db.init_app(app)
default_value = '0000000001'

class Userdata(db.Model):
    __tablename__ = 'Userdata'
    userUid = db.Column(db.Integer, db.ForeignKey(User.User.uid), primary_key=True,  nullable=False)
    anger = db.Column(db.String(20))
    fear = db.Column(db.String(20))
    happiness = db.Column(db.String(20))
    sadness = db.Column(db.String(20))
    surprise = db.Column(db.String(20))
    

    def __init__(self, userUid, 
                 anger = default_value, 
                 fear = default_value, 
                 happiness = default_value, 
                 sadness = default_value, 
                 surprise = default_value):
        self.userUid = userUid
        self.anger = anger
        self.fear = fear
        self.happiness = happiness
        self.sadness = sadness
        self.surprise = surprise


    def __str__(self):
        return f" userUid: {self.userUid}\n anger:\t\t{self.anger}\n fear:\t\t{self.fear}\n happiness:\t{self.happiness}\n sadness:\t{self.sadness}\nsurprise:\t{self.surprise}" 
    
    def toDict(self):
        userdataDict = {
            "anger" : self.anger.decode(),
            "fear" : self.fear.decode(),
            "happiness" : self.happiness.decode(),
            "sadness" : self.sadness.decode(),
            "surprise" : self.surprise.decode(),
        }

        return userdataDict
    

def create_table():
    with app.app_context():
        db.create_all()

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
    
def select_userdata_with_uid(uid):
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




if __name__ == "__main__":
    print_all_userdatas_list()
    # data = select_userdata_with_uid(2)
    # data.happiness = b'0000011001'
    # update_userdata_with_uid(2, data)
    #print(data)
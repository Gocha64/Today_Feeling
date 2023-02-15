from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from dotenv import load_dotenv
from urllib.parse import quote  
from models.User import User
from extensions import db


default_value = '0000000001'

class Userdata(db.Model):
    __tablename__ = 'Userdata'
    userUid = db.Column(db.Integer, db.ForeignKey(User.uid), primary_key=True,  nullable=False)
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

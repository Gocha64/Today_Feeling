from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import time
from datetime import datetime, timedelta
from models.User import User
from models.Playlist import Playlist
from extensions import db


class Statistics(db.Model):
    __tablename__ = 'Statistics'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    userUID = db.Column(db.Integer, db.ForeignKey(User.uid), unique=True, nullable=False )
    dateTime = db.Column(db.DateTime(timezone=True), nullable=False)
    songUID = db.Column(db.Integer, db.ForeignKey(Playlist.uid), unique=True, nullable=False )
    emotion = db.Column(db.Integer, nullable=False)
    

    def __init__(self, userUID, songUID, emotion, date = datetime.now()):
        self.userUID = userUID
        self.songUID = songUID
        self.dateTime = date
        self.emotion = emotion

    def __str__(self):
        return f" uid: {self.uid}\n userUID: {self.userUID}\n datetime: {self.dateTime}\n songUID: {self.songUID}\n emotion: {self.emotion}\n"
    
    
    def toDict(self):
        staDict = dict()
        # staDict['userUID'] = self.userUID
        staDict['songUID'] = self.songUID
        staDict['datetime'] = self.dateTime
        staDict['timestamp'] = int(time.mktime(self.dateTime.timetuple()))
        staDict['emotion'] = self.emotion
        return staDict


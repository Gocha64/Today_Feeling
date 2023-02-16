from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.sql.expression import func
from extensions import db

class Playlist(db.Model):
    __tablename__ = 'Playlists'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    urlWeb = db.Column(db.String(50))
    title = db.Column(db.String(50))
    genre = db.Column(db.Integer, nullable=False)
    

    def __init__(self, urlWeb, title, genre):
        self.urlWeb =urlWeb
        self.title = title
        self.genre = genre


    def __str__(self):
        return f" uid: {self.uid}\n urlWeb: {self.urlWeb}\n title: {self.title}\n genre: {self.genre}\n"

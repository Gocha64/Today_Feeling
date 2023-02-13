from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dotenv import load_dotenv
import os
from urllib.parse import quote  


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
    

def create_table():
    with app.app_context():
        db.create_all()

def print_all_playlist_list():
    with app.app_context():
        playlists = db.session.query(Playlist).all()
        for playlist in playlists:
            print(playlist)

def select_playlist():
    with app.app_context():
        playlists = db.session.query(Playlist).all()
    return playlists


def select_playlist_with_uid(uid):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.uid == uid).first()
    return playlist

def select_playlist_with_title(title):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.title == title).first()
    return playlist


def get_songUrl_with_uid(uid):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.uid == uid).first()
    return playlist.urlWeb


def insert_playlist(playlist):
    try:
        with app.app_context():
            db.session.add(playlist)
            db.session.commit()
    except Exception as e:
        print(e.args)

def delete_playlist_with_uid(uid):
    try:
        with app.app_context():
            playlist = db.session.query(Playlist).filter(Playlist.uid == uid).first()
            if playlist == None:
                return None
            
            db.session.delete(playlist)
            db.session.commit()
    except Exception as e:
        print(e.args)

def update_playlist_with_uid(uid, playlistModi):
    try:
        with app.app_context():
            playlist = db.session.query(Playlist).filter(Playlist.uid == uid).first()
            if playlist == None:
                return None

            playlist.urlWeb = playlistModi.urlWeb
            playlist.title = playlistModi.title
            playlist.genre = playlistModi.genre
            db.session.commit()
    except Exception as e:
        print(e.args)

if __name__ == "__main__":
    #playlist = Playlist("urlTest", "titleTest", 1, "000001")
    #insert_playlist(playlist)
    #modiTest = Playlist("urlEx","appEx", "titleEx", 1, "000111")
    #delete_playlist_with_uid(4)
    #update_playlist_with_uid(3, modiTest)
    print_all_playlist_list()

    #playlist1 = select_playlist_with_uid(3)
    #print(playlist1)
    
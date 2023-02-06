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

app.config["SQLALCHEMY_DATABASE_URI"] = f"mariadb+pymysql://{db_userName}:{db_pw}@{db_ip}:{db_port}/{db_schema}"
# initialize the app with the extension
db.init_app(app)

class Playlist(db.Model):
    __tablename__ = 'playlists'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    title = db.Column(db.String(50), unique=True, nullable=False)
    genre = db.Column(db.String(50), nullable=False)
    mode = db.Column(db.Integer, nullable=False)
    

    def __init__(self, title, genre, mode):
        self.title = title
        self.genre = genre
        self.mode = mode


    def __str__(self):
        return f" uid: {self.uid}\n title: {self.title}\n genre: {self.genre}\n mode: {self.mode}"
    

def create_table():
    with app.app_context():
        db.create_all()

def print_all_users_list():
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


def insert_playlist(playlist):
    try:
        with app.app_context():
            db.session.add(playlist)
            db.session.commit()
    except Exception as e:
        print(e.args)


if __name__ == "__main__":
    playlist = Playlist("titleEx", "genreEx", 1)
    insert_playlist(playlist)
    playlist1 = select_playlist_with_uid(1)
    print(playlist1)
    
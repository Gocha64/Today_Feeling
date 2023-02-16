from sqlalchemy.sql.expression import func
from models.Playlist import Playlist
from extensions import db
from flask import current_app as app


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

def select_playlist_with_title( title):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.title == title).first()
    return playlist


def get_songUrl_with_uid(uid):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.uid == uid).first()
    return playlist.urlWeb

def get_random_playlist():
    with app.app_context():
        playlist = db.session.query(Playlist).order_by(func.random()).first()
    return playlist

def get_random_playlist_from_genreList(genreList):
    with app.app_context():
        playlist = db.session.query(Playlist).filter(Playlist.genre.in_(genreList)).\
                                                      order_by(func.random()).first()
    return playlist

def insert_playlist(playlist):
    try:
        with app.app_context():
            db.session.add(playlist)
            db.session.commit()
    except Exception as e:
        print(e.args)

def delete_playlist_with_uid( uid):
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

    
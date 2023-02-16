from flask_sqlalchemy import SQLAlchemy
from flask import Flask
from models.User import User
from extensions import db
from flask import current_app as app


def print_all_users_list():
    with app.app_context():
        users = db.session.query(User).all()
        for user in users:
            print(user)

def select_users():
    with app.app_context():
        users = db.session.query(User).all()
    return users


def select_user_with_uid(uid):
    with app.app_context():
        user = db.session.query(User).filter(User.uid == uid).first()
    return user

def select_user_with_id(id):
    with app.app_context():
        user = db.session.query(User).filter(User.id == id).first()
    return user

def select_user_with_name(name):
    with app.app_context():
        user = db.session.query(User).filter(User.name == name).first()
    return user


def select_user_with_email(email):
    with app.app_context():
        user = db.session.query(User).filter(User.email == email).first()
    return user

def insert_user(user):
    try:
        with app.app_context():
            db.session.add(user)
            db.session.commit()
    except Exception as e:
        print(e.args)
        raise e

def delete_user_with_uid(uid):
    try:
        with app.app_context():
            user = db.session.query(User).filter(User.uid == uid).first()
            if user == None:
                return None

            db.session.delete(user)
            db.session.commit()
    except Exception as e:
        print(e.args)

def update_user_with_uid(uid, userModi):
    # id 중복 같은건 사용할 떄 걸러낼 것

    try:
        with app.app_context():
            user = db.session.query(User).filter(User.uid == uid).first()
            if user == None:
                return None
            
            user.id = userModi.id
            user.password = userModi.password
            user.name = userModi.name
            user.sex = userModi.sex
            user.email = userModi.email
            
            db.session.commit()
    except Exception as e:
        print(e.args)


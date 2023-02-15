from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from extensions import db

class User(db.Model):
    __tablename__ = 'Users'
    uid = db.Column(db.Integer, primary_key=True, nullable=False, autoincrement=True)
    id = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(50), nullable=False)
    name = db.Column(db.String(50), nullable=False)
    sex = db.Column(db.Integer, nullable=False)
    email = db.Column(db.String(50), nullable=False)
    

    def __init__(self, id, password, name, sex, email):
        self.id = id
        self.password = password
        self.name = name
        self.sex = sex
        self.email = email


    def __str__(self):
        return f" uid: {self.uid}\n id: {self.id}\n name: {self.name}\n password: {self.password}\n sex: {self.sex}\n email: {self.email}\n"
    
    def toDict_without_password(self):
        userDict = {
            "uid" : self.uid,
            "id" : self.id,
            "name" : self.name,
            "sex" : self.sex,
            "email" : self.email
        }
        return userDict

    


from dotenv import load_dotenv
from flask_sqlalchemy import SQLAlchemy
import os
from urllib.parse import quote  


db = SQLAlchemy()


load_dotenv()
db_userName = os.getenv("DB_USER")
db_pw = os.getenv("DB_PW")
db_ip = os.getenv("DB_IP")
db_port = os.getenv("DB_PORT")
db_schema = os.getenv("DB_SCHEMA")

db_url = f'mysql+pymysql://{db_userName}:%s@{db_ip}:{db_port}/{db_schema}' % quote(db_pw)


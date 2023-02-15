from flask import Blueprint

bp = Blueprint('main', __name__)

from main import app_main
from main import app_member
from main import app_modify
from main import app_register
from main import app_statistics
from main import app_login
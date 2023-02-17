import pymysql
import pandas as pd
import logging
from datetime import datetime
import os
from pathlib import Path

df=None
conn = None
logger = None

def makeLogger():
    global logger
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    stream_handler = logging.StreamHandler()
    stream_handler.setFormatter(formatter)
    logger.addHandler(stream_handler)
    now = datetime.now().strftime('%y%m%d_%H%M%S')
    if(not(os.path.exists('logs'))):
        os.makedirs('logs')
    fileName = './logs/'+now+'.log'
    file_handler = logging.FileHandler(fileName)
    file_handler.setFormatter(formatter)
    
    logger.addHandler(file_handler)
    

def readExcel():
    print("open data.xlsx")
    global df
    print(os.getcwd())
    try:
        df = pd.read_excel("data.xlsx",engine="openpyxl")
    except:
        print("open data.xlsx KO")
        return -1
    print("open data.xlsx OK")

#we have to change (', " ,%) to (\', \", \%) to avoid sql query error and we have to limit its length
def modifyTitle(title):
    title = title.replace("'",r"\'").replace('"',r"\"").replace("%",r"\%")
    title = f'{title:.100}'
    return title

def buildSQL(param1,param2,param3):
    param2 = modifyTitle(param2)
    sql = "insert into Playlists(urlWeb,title,genre) values "+"(\""+str(param1)+"\",\""+str(param2)+"\",\""+str(param3)+"\")"
    return sql


def connDB():
    global conn
    host,user,password,db,port = input("input host, user, password, db, port(split with space) : ").split()
    print("conn to DB")
    try:
        conn = pymysql.connect(host=host,
                       user=user,
                       password=password,
                       db=db,
                       charset='utf8',
                       port=int(port))
    except:
        print("connDB KO")
        return -1
    print("connDB OK")

def insertData():
    logger.info("Insert data start")
    cur = conn.cursor()
    insertOK=0
    insertKO=0
    for idx,row in df.iterrows():
        sql = buildSQL(row['url'],row['title'],row['genre'])
        try:
            cur.execute(sql)
            logger.info("INSERT OK sql : "+sql)
            insertOK+=1
        except pymysql.err.IntegrityError as e:
            insertKO+=1
            code, msg = e.args
            if(code==1062):
                logger.warn("url Duplicated with sql : "+sql)
            else:
                logger.warn("Error with sql : "+sql)    
        except:
            insertKO+=1
            logger.warn("Error with sql : "+sql)
    conn.commit()
    conn.close()
    logger.info("total elements : "+str(len(df)))
    logger.info("insert OK : "+str(insertOK))
    logger.info("insert KO : "+str(insertKO))
    
def main():
    currPath = os.path.realpath(__file__)
    rootPath = Path(currPath).parent
    os.chdir(rootPath)
    
    if(connDB()==-1):
        print("terminate")
        return
    if(readExcel()==-1):
        print("terminate")
        return
    makeLogger()
    insertData()

if __name__=="__main__":
    main()
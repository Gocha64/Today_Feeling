from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
import re
from urllib.parse import urlparse, parse_qs
import time
import pandas as pd
import os
from pathlib import Path
#todo1 refactoring
#todo2 string 'title' length limit
#todo3 limit userInput of genre
#todo4 write readme
#readme를 참조
#chrome.exe --remote-debugging-port=65489 --user-data-dir="C:/ChromeTEMP

playListNameXpath= '//*[@id="header"]/ytmusic-detail-header-renderer/div/div[2]/h2/yt-formatted-string'
songNumXpath = '//*[@id="header"]/ytmusic-detail-header-renderer/div/div[2]/yt-formatted-string[1]/span[1]'

#edit @param from 1 to songNum
songElementXpath = '//*[@id="contents"]/ytmusic-responsive-list-item-renderer[@param]/div[2]/div[1]/yt-formatted-string/a'

driver=None
urlBefore=''
df=None
dfDataFormat1 = {'url' : [],'title':[]}

def modifyURL(url):
    parse_result = urlparse(url)
    params = parse_qs(parse_result.query)
    return params['v'][0]

def connChrome():
    print("connecting Chrome")
    global driver
    chrome_options=Options()
    chrome_options.add_experimental_option("debuggerAddress","127.0.0.1:65489")
    driver=webdriver.Chrome(options=chrome_options)
    driver.implicitly_wait(10)
    driver.get('https://music.youtube.com/')
    print("connecting Chrome OK\n")
    
def parseHTML():
    global urlBefore
    global driver
    global df
    print("playList detector started")
    print("go to playList\n")
    while(True):
        time.sleep(1)
        
        if('playlist' in driver.current_url and (not urlBefore == driver.current_url)):
            try:
                num = driver.find_element(By.XPATH,songNumXpath)
                playListTitle = driver.find_element(By.XPATH,playListNameXpath)
            except:
                print("not support user-editable playlist")
                continue
            temp = re.sub(r'[^0-9]','',num.text)
            print("playList detected : ",playListTitle.text)
            print("songs detected : " ,temp)
            #because of lazyload things we have to scroll down
            for j in range(0,3):
                time.sleep(0.5)
                driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
            tempdf = pd.DataFrame(dfDataFormat1)

            print("parsing start")
            for i in range(1,int(temp)+1):
                tempXpath = songElementXpath.replace('@param',str(i))
                tempElement = driver.find_element(By.XPATH,tempXpath)
                title = tempElement.text
                url = modifyURL(tempElement.get_attribute('href'))
                tempdf.loc[len(tempdf)]=[url,title]
            urlBefore=driver.current_url
            print("parsing OK")
            a = input("genre of current playlist(quit : -1) : ")
            if(a=="-1"):
                print("quit playlist detector\n")
                return
            tempdf['genre']=a
            
            df = pd.concat([df,tempdf])
            print("save OK")
            print("go to other playlist\n")
            
def readExcel():
    global df
    print("open data.xlsx")
    try:  
        df = pd.read_excel("data.xlsx",engine="openpyxl")
    except FileNotFoundError:
        print("no data.xlsx")
    else:
        print("open data.xlsx OK")
    
def writeExcel():
    print("start writeExcel")
    global df
    df.to_excel("data.xlsx",index=False)
    print("writeExcel OK")

def rmDup():
    print("start removing Duplicated url")
    global df
    df = df.drop_duplicates(['url'],keep='first')
    print("removing Duplicated url OK")


currPath = os.path.realpath(__file__)
rootPath = Path(currPath).parent
os.chdir(rootPath)

readExcel()
connChrome()
parseHTML()
rmDup()
writeExcel()


    

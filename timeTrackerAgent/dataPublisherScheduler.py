# Schedule Library imported
import schedule
import time
import requests
import os

lastSentData = ''
isDataPublishOFF = False
def publishData():
    with open("mouse_log.txt", "r") as file:
        for last_line in file:
            pass
    trimmedLastLine = last_line.strip()
    global lastSentData, isDataPublishOFF
    print('Pre--' + lastSentData)
    print('isDataPublishOFF=', isDataPublishOFF)
    if not trimmedLastLine.__eq__(lastSentData) or not isDataPublishOFF:
        print(isDataPublishOFF, " Working")
        print('lastSentData=' + lastSentData + ' and ' + 'trimmedLastLine=' + trimmedLastLine)
        spiltedstampandcoord = trimmedLastLine.split("&")
        coordinates = spiltedstampandcoord[1].strip().split(":")
        requestJson = {"userId": os.getlogin(), "mouseClickTime": spiltedstampandcoord[0], "xCoordinate": coordinates[0], "yCoordinate": coordinates[1]}
        if trimmedLastLine.__eq__(lastSentData):
            isDataPublishOFF = True
        else:
            isDataPublishOFF = False
        lastSentData = trimmedLastLine
        try:
            response = requests.post("http://localhost:8087/event/data", json=requestJson)
            print(response.status_code)
            print(response.json())
        except:
            print("Connection Refused")


schedule.every(3).seconds.do(publishData)

while True:
    schedule.run_pending()
    time.sleep(1)

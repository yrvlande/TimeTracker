# Schedule Library imported
import schedule
import time
import requests
import os
import atexit


lastSentData = ''
def publishData():
    trimmedLastLine = ''
    with open("mouse_log.txt", "r") as file:
        try:
            for last_line in file:
                pass
            trimmedLastLine = last_line.strip()
        except:
            print("File is empty. Event Listener is not started yet.")
        finally:
            file.close()

    global lastSentData
    print('lastSentData=' + lastSentData + ' and ' + 'trimmedLastLine=' + trimmedLastLine)
    if not trimmedLastLine.__eq__(lastSentData):
        print("Sending data ON")
        spiltedstampandcoord = trimmedLastLine.split("&")
        coordinates = spiltedstampandcoord[1].strip().split(":")
        requestJson = {"userId": os.getlogin(), "mouseClickTime": spiltedstampandcoord[0], "xCoordinate": coordinates[0], "yCoordinate": coordinates[1]}
        lastSentData = trimmedLastLine
        try:
            response = requests.post("http://localhost:8087/event/data", json=requestJson)
            print(response.status_code)
            print(response.json())
        except:
            print("Connection Refused")


def clean_file():
    open("mouse_log.txt", "r+").truncate(0)


schedule.every(5).minute.do(publishData)
atexit.register(clean_file)

while True:
    schedule.run_pending()
    time.sleep(1)

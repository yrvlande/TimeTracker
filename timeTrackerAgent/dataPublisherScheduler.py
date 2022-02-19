# Schedule Library imported
import schedule
import time
import requests


def publishData():
    with open("mouse_log.txt", "r") as file:
        for last_line in file:
            pass
    trimmed = last_line.strip()
    spiltedstampandcoord = trimmed.split("&")
    coordinates = spiltedstampandcoord[1].strip().split(":")
    requestJson = {"userId": "e1078064", "mouseClickTime": spiltedstampandcoord[0], "xCoordinate": coordinates[0],
                   "yCoordinate": coordinates[1]}
    try:
        response = requests.post("http://localhost:8087/event/data", json=requestJson)
        print(response.status_code)
        print(response.json())
    except:
        print("Connection Refused")


schedule.every(1).seconds.do(publishData)

while True:
    schedule.run_pending()
    time.sleep(1)

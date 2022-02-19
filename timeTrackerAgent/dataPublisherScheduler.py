# Schedule Library imported
import schedule
import time
import requests
from datetime import datetime
import calendar;
from datetime import timezone


def publishData():
    requestJson = {"id": 4, "userId": "e1078064", "mouseClickTime": "2022-02-17T13:10:17", "xCoordinate": 10,
                   "yCoordinate": 11, "insertedOn": round(time.time() * 1000)}
    response = requests.post("http://localhost:8087/event/data", json=requestJson)


schedule.every(10).seconds.do(publishData)

while True:
    schedule.run_pending()
    time.sleep(1)

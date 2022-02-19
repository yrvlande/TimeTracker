# Schedule Library imported
import schedule
import time
import requests


def publishData():
    requestJson = {"id": 5, "userId": "e1078064", "mouseClickTime": "2022-02-17T13:10:17", "xCoordinate": 10,
                   "yCoordinate": 11}
    response = requests.post("http://localhost:8087/event/data", json=requestJson)


schedule.every(10).seconds.do(publishData)

while True:
    schedule.run_pending()
    time.sleep(1)

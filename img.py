import cv2
import numpy as np
import argparse
import matplotlib.pyplot as plt

ap = argparse.ArgumentParser()
ap.add_argument("-i", "--image", help = "Your image file here")
args = vars(ap.parse_args())

img = cv2.imread(args["image"])

hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

sensitivity = 17
lower_red = np.array([60 - sensitivity,100,0])
upper_red = np.array([60 + sensitivity,255,255])

mask = cv2.inRange(hsv,lower_red,upper_red)

res = cv2.bitwise_and(img,img,mask = mask)

cv2.imshow('ORIGINAL IMAGE',img)
#cv2.imshow('MASK',mask)
#cv2.imshow('FILTERED IMAGE',res)
#cv2.waitKey(0)
#cv2.destroyAllWindows()

bgr = res
#bgr = cv2.cvtColor(res, cv2.COLOR_HSV2BGR)
plt.imshow(bgr)
plt.show()

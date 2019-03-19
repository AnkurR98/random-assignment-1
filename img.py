# -*- coding: utf-8 -*-
"""
Created on Wed Mar 20 01:46:07 2019

@author: ankur
"""

import cv2
import numpy as np
import matplotlib.pyplot as plt

img = cv2.imread('./sampleimage.jpg')

hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

sensitivity = 16
lower_red = np.array([60 - sensitivity,100,0])
upper_red = np.array([60 + sensitivity,255,255])

mask = cv2.inRange(hsv,lower_red,upper_red)

res = cv2.bitwise_and(img,img,mask = mask)

cv2.imshow('ORIGINAL IMAGE',img)
#cv2.imshow('MASK',mask)
#cv2.imshow('FILTERED IMAGE',res)
#cv2.waitKey(0)
#cv2.destroyAllWindows()

bgr = cv2.cvtColor(img, cv2.COLOR_HSV2BGR)
plt.imshow(res)
plt.show()
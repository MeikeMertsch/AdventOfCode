(ns christmas.chr20.day21-test
  (:require [christmas.chr20.day21 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day21 real")
(def filename "resources/chr20/day21")

(expect 5 (chr/day21 filename))
;(expect 2150 (chr/day21 realfilename))

(expect "mxmxvkd,sqjhc,fvjkl" (chr/day21b filename))
(expect "vpzxk,bkgmcsx,qfzv,tjtgbf,rjdqt,hbnf,jspkl,hdcj" (chr/day21b realfilename))
)
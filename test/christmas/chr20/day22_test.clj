(ns christmas.chr20.day22-test
  (:require [christmas.chr20.day22 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))


  (def realfilename "resources/chr20/day22 real")
(def filename "resources/chr20/day22")

(expect 5 (chr/day22 filename))
;(expect 2150 (chr/day22 realfilename))


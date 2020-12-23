(ns christmas.chr20.day18-test
  (:require [christmas.chr20.day18 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment   (def realfilename "resources/chr20/day18 real")
(def filename "resources/chr20/day18")

(expect (+ 51 26 437 12240 13632) (chr/day18 filename))
(expect 5019432542701 (chr/day18 realfilename))

 )  
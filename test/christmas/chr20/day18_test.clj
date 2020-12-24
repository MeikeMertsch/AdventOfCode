(ns christmas.chr20.day18-test
  (:require [christmas.chr20.day18 :as chr]
            [expectations :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]))
(comment
(def realfilename "resources/chr20/day18 real")
(def filename "resources/chr20/day18")

;(expect (+ 51 26 437 12240 13632) (chr/day18 filename))
;(expect 5019432542701 (chr/day18 realfilename))

(expect (+ 231 51 46 1445 669060 23340 0) (chr/day18b filename))
(expect 70518821989947 (chr/day18b realfilename)) ;  70518821989947 too low


;(pp/pprint (chr/day18b realfilename))
;(pp/pprint (chr/day18b filename))
)
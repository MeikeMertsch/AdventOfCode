(ns christmas.chr15.day04-test
  (:require [christmas.chr15.day04 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def input "ckczppom")
(def input-t1 "abcdef")
(def input-t2 "pqrstuv")

(expect "000001dbbfa3a5c83a2d506429c7b00e" (chr/digest (str input-t1 609043)))

(expect 609043 (chr/day04 input-t1 "00000"))
(expect 117946 (chr/day04 input "00000"))
(expect 3938038 (chr/day04 input "000000"))
)
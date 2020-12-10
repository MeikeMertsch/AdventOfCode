(ns christmas.chr20.day03-test
	(:require [christmas.chr20.day03 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))


(comment
  (def filename "resources/chr20/day03")
(def realfilename "resources/chr20/day03real")
(def game (chr/day03 filename))

(expect 7 (chr/day03 filename))
(expect 178 (chr/day03 realfilename))

(expect 336 (chr/day03b filename [[1 1] [3 1] [5 1] [7 1] [1 2]]))
(expect 336 (chr/day03b realfilename [[1 1] [3 1] [5 1] [7 1] [1 2]])) ; 6985040400 too high
(expect 336 (chr/day03b realfilename [[1 1] [3 1] [5 1] [7 1] [1 2]])) ; 4388038200 too high
)
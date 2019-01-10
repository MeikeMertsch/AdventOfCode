(ns christmas.chr15.day25-test
  (:require [christmas.chr15.day25 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment

(def row 2978)
(def column 3083)

(expect 18 (chr/position 3 4))

(expect 21345942 (chr/day25 3 4))
(expect 2650453 (chr/day25 column row))
)
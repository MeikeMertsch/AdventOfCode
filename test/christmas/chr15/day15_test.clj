(ns christmas.chr15.day15-test
  (:require [christmas.chr15.day15 :as chr]
  		  	[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment

(defn parse-line [line]
	(map t/parse-int (re-seq #"-?\d+" line)))

(defn parse-file [text]
	(->> (str/split-lines text)
		 (map parse-line)))

(def file (parse-file (slurp "resources/christmas/chr15/day15real")))
(def t-file (parse-file "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"))

(expect 360 (chr/total-score t-file [2 3]))
(expect 0 (chr/total-score t-file [2 10]))
(expect 62842880 (chr/total-score t-file [44 56]))

;(expect 18965440 (chr/day15 file))
;(expect 15862900 (chr/day15b file))
)
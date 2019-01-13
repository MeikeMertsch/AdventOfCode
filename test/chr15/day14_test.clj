(ns christmas.chr15.day14-test
  (:require [christmas.chr15.day14 :as chr]
  		  	[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(defn get-data [input]
	(->> (str/split-lines input)
		 (map #(map t/parse-int (re-seq #"\d+" %)))))

(def file (get-data (slurp "resources/christmas/chr15/day14real")))

(def test-string (get-data "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."))

(expect 1120 (chr/day14 test-string 1000))
(expect 2640 (chr/day14 file 2503))

(expect 689 (chr/day14b test-string 1000))
(expect 1102 (chr/day14b file 2503))
)
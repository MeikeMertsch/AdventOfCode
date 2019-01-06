(ns christmas.chr15.day06-test
  (:require [christmas.chr15.day06 :as chr]
  			[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(defn parse-file [input]
	(->> (slurp input)
		 str/split-lines
		 (map (partial re-seq #"on|off|toggle|\d+"))
		 (map #(vector (keyword (first %))(map t/parse-int (rest %))))))

(def file (parse-file "resources/christmas/chr15/day06real"))
(expect 300 (count file))

(expect 10000 (count (chr/create-grid 100)))
(expect 1000000 (count (chr/create-grid 1000)))

(expect-focused 543903 (chr/day06 file))
(expect-focused 14687245 (chr/day06b file))
)
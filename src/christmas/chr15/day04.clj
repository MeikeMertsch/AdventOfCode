(ns christmas.chr15.day04
  (:require [clojure.string :as str]
  			[digest :as dig]))

(defn digest [input]
	(dig/md5 input))

(defn day04 [input start]
	(->> (map #(digest (str input %)) (range))
		 (take-while #(not (str/starts-with? % start)))
		 count))
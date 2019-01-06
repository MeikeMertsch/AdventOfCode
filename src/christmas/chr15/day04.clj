(ns christmas.chr15.day04
  (:require [clojure.string :as str]
  			[digest :as dig]))

;(digest/md5 "foo")

(defn digest [input]
	(dig/md5 input))

(defn day04 [input start]
	(->> (reductions #(digest (str input %2)) "" (range))
		 (take-while #(not (str/starts-with? % start)))
		 count
		 dec))
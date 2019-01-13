(ns christmas.chr16.day06
  (:require [clojure.string :as str]))

(defn most-common [column]
	(->> (frequencies column)
		 (sort-by last >)
		 ffirst))

(defn day06 [input]
	(->> (str/split-lines input)
		 (apply map vector)
		 (map most-common)
		 (apply str)))

(defn least-common [column]
	(->> (frequencies column)
		 (sort-by last)
		 ffirst))

(defn day06b [input]
	(->> (str/split-lines input)
		 (apply map vector)
		 (map least-common)
		 (apply str)))
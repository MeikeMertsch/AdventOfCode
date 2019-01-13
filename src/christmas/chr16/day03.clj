(ns christmas.chr16.day03
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn parse-line [line]
	(->> (re-seq #"\d+" line)
		 (map t/parse-int)))

(defn triangle? [[a b c]]
	(< c (+ a b)))

(defn day03 [input]
	(->> (str/split-lines input)
		 (map parse-line)
		 (map sort)
		 (filter triangle?)
		 count))

(defn day03b [input]
	(->> (str/split-lines input)
		 (map parse-line)
		 (apply mapcat vector)
		 (partition 3)
		 (map sort)
		 (filter triangle?)
		 count))

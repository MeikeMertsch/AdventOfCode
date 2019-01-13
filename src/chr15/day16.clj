(ns christmas.chr15.day16
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(def regex #"goldfish|children|samoyeds|vizslas|perfumes|akitas|pomeranians|cars|trees|cats")

(defn parse-sue [sue]
	(->> (re-seq regex sue)
		 (cons "number")
		 (#(zipmap % (map t/parse-int (re-seq #"\d+" sue))))))

(defn matches? [sue]
	(and (= (sue "children" 3) 3)
		 (= (sue "cats" 7) 7)
		 (= (sue "samoyeds" 2) 2)
		 (= (sue "pomeranians" 3) 3)
		 (= (sue "akitas" 0) 0)
		 (= (sue "vizslas" 0) 0)
		 (= (sue "goldfish" 5) 5)
		 (= (sue "trees" 3) 3)
		 (= (sue "cars" 2) 2)
		 (= (sue "perfumes" 1) 1)))

(defn matches-2? [sue]
	(and (= (sue "children" 3) 3)
		 (> (sue "cats" 100) 7)
		 (= (sue "samoyeds" 2) 2)
		 (< (sue "pomeranians" 0) 3)
		 (= (sue "akitas" 0) 0)
		 (= (sue "vizslas" 0) 0)
		 (< (sue "goldfish" 0) 5)
		 (> (sue "trees" 100) 3)
		 (= (sue "cars" 2) 2)
		 (= (sue "perfumes" 1) 1)))

(defn day16 [string]
	(->> (str/split-lines string)
		 (map parse-sue)
		 (filter matches?)
		 first
		 (#(% "number"))))

(defn day16b [string]
	(->> (str/split-lines string)
		 (map parse-sue)
		 (filter matches-2?)
		 first
		 (#(% "number"))
		 ))


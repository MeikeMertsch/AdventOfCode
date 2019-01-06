(ns christmas.chr15.day08
  (:require [clojure.string :as str]))

(defn count-literals [input]
	(-> (str/replace input #"\\\\" "n")
		(str/replace #"\\x.." "1")
		read-string
		count))

(defn day08 [input]
	(-  (apply + (map count input))
		(apply + (map count-literals input))))

(defn count-escaped [input]
	(->> (str/escape input {\\ "nn" \" "nn"})
		 count
		 (+ 2)))

(defn day08b [input]
	(-  (apply + (map count-escaped input))
		(apply + (map count input))))
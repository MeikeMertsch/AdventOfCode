(ns christmas.chr16.day04
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn decoy? [room checksum]
	(->> (str/replace room #"\-" "")
		 frequencies
		 (sort-by first #(< (int %2) (int %)))
		 (sort-by last)
		 reverse
		 (map first)
		 (take 5)
		 (not= (seq checksum))))

(defn decoy [[room sector checksum]]
	(if (decoy? room checksum) 0 (t/parse-int sector)))

(defn parse-line [line]
	(->> (re-seq #"\d+|[a-z\-]+" line)
		 decoy))

(defn day04 [file]
	(->> (str/split-lines file)
		 (map parse-line)
		 (apply +)))

(def cipher {\a \b \b \c \c \d \d \e \e \f \f \g \g \h \h \i \i \j \j \k \k \l \l \m \m \n \n \o \o \p \p \q \q \r \r \s \s \t \t \u \u \v \v \w \w \x \x \y \y \z \z \a \- \space})

(def value (apply hash-map (apply concat (map-indexed vector (seq "abcdefghijklmnopqrstuvwxyz")))))

(defn next-char [sector character]
	(-> (int character)
		(+ sector -97)
		(rem 26)
		value))

(defn decrypt [room sector]
	(->> (map (partial next-char sector) room)
		 (apply str)))

(defn parse-room [line]
	(let [room (re-seq #"\d+|[a-z\-]+" line)
		  room-name (first room)
		  sector (t/parse-int (second room))] 
		[(decrypt room-name sector) sector]))

(defn day04b [file]
	(->> (str/split-lines file)
		 (map parse-room)
		 (filter #(str/includes? % "north"))
		 first
		 second))
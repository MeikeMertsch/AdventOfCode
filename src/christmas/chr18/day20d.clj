(ns christmas.chr18.day20d
	(:require [clojure.string :as str]))

(defn find-left [text]
	(re-find #"[SWEN]+\([SWEN\|]+\)" text))

(defn find-right [text]
	(re-find #"\([SWEN\|]+\)[SWEN]+" text))

(defn find-split [text]
	(re-find #"\([SWEN\|]+\)\([SWEN\|]+\)" text))

(defn find-packed [text]
	(re-find #"[\(\|]\([SWEN\|]+\)[\)\|]" text))

(defn assemble [[prefix & parts]]
	(->> (repeat prefix)
		 (map #(str %2 %1) parts)
		 (str/join "|")
		 (#(str "(" % ")"))))

(defn assemble-r [parts]
	(let [suffix (last parts)
		  ways (butlast parts)]
	(->> (repeat suffix)
		 (map #(str %1 %2) ways)
		 (str/join "|")
		 (#(str "(" % ")")))))

(defn pattern [text]
	(->> (str/escape text {\( "\\(" \) "\\)" \| "\\|" \X ""})
		 re-pattern))

(defn replace-left [text]
	(let [orig (find-left text)
		  tinker (str/replace orig #"\|\)" "|X)")]
		(-> (#(str/split tinker #"[\(\)\|]"))
			assemble
			(str/replace #"X" "")
			(#(str/replace text (pattern orig) %)))))

(defn replace-right [text]
	(let [orig (find-right text)
		  tinker (str/replace orig #"\|\)" "|X)")]
		(-> (#(str/split tinker #"[\(\)\|]"))
			assemble-r
			(str/replace #"X" "")
			(#(str/replace text (pattern orig) %)))))

(defn replace-split [text]
	(let [orig (find-split text)
		  tinker (str/replace orig #"\|\)" "|X)")
		  split-up (map #(str/escape % {\( "" \) ""}) (str/split tinker #"\)\("))]
		  (->> (for [prefix (str/split (first split-up) #"\|")
		  			 suffix (str/split (last split-up) #"\|")]
					(str prefix suffix))
		  		(str/join "|")
		  		(#(str "(" % ")"))
		  		(#(str/replace % #"X" ""))
				(str/replace text (pattern orig))
				)))

(defn replace-packed [text]
	(let [orig (find-packed text)
		  tinker (str/replace orig #"\|\)" "|X)")]
		(-> (str/replace tinker #"\(\(" "(")
			(str/replace #"\|\(" "|")
			(str/replace #"\)\|" "|")
			(str/replace #"\)\)" ")")
		  	(str/replace #"X" "")
			(#(str/replace text (pattern orig) %))
				)))

(defn tick [text]
	(cond
		(find-left text) (replace-left text)
		(find-right text) (replace-right text)
		(find-split text) (replace-split text)
		(find-packed text) (replace-packed text)))

(defn day20 [text]
	(->> (iterate tick text)
		 (drop 1000)
		 ;(drop 3)
		 first))
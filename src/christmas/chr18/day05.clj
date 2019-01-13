(ns christmas.chr18.day05
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))

(defn cas [char1]
	(str/lower-case char1))

;(defn match [char1 char2]
;	(and (not (nil? char2))
;		 (not= char1 char2)
;		 (= (cas char1) (cas char2))))
(defn match [char1 char2]
	(and (not (nil? char2))
		 (= 32 (Math/abs (- (int char1) (int char2))))))

(defn treat [my_set char1]
	(let [char2 (first my_set)]
		(if (match char1 char2)
			(rest my_set)
			(cons char1 my_set))))

(defn fold [my_string]
	(reduce treat '() my_string))

(defn exercise05 [my_string]
	(count (fold my_string)))


;-------------------------------------

(defn reduceString [my_string char1]
	(->> (str/replace my_string (re-pattern char1) "")
		(reduce treat '())))

(defn exercise05b [my_string]
	(->> (map #(reduceString my_string %) '("[Aa]" "[Bb]" "[Cc]" "[Dd]" "[Ee]" "[Ff]" "[Gg]" "[Hh]" "[Ii]" "[Jj]" "[Kk]" "[Ll]" "[Mm]" "[Nn]" "[Oo]" "[Pp]" "[Qq]" "[Rr]" "[Ss]" "[Tt]" "[Uu]" "[Vv]" "[Ww]" "[Xx]" "[Yy]" "[Zz]"))
		 (map count)
		 (apply min)))
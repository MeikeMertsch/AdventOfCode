(ns christmas.chr17.day02-test
	(:require [christmas.chr17.day02 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))
(comment
(defn parse [char1]
	(int (bigint (str char1))))

(defn getFile [fname]
	(->> (slurp fname)
		 (str/split-lines)
		 (map #(str/split % #" "))
		 (map #(map parse %))))


(def file (getFile "resources/chr17/day02"))
(def file-b (getFile "resources/chr17/day02b"))
(def realFile (getFile "resources/chr17/day02real"))

(expect [[5 1 9 5] [7 5 3] [2 4 6 8]] file)

(expect 8 (chr/calculate-row (first file)))
(expect 4 (chr/calculate-row (second file)))
(expect 6 (chr/calculate-row (last file)))

(expect 18 (chr/exercise02 file))
(expect 42299 (chr/exercise02 realFile))

;-----------------------

(expect 4 (chr/deal-with-row (first file-b)))
(expect 3 (chr/deal-with-row (second file-b)))
(expect 2 (chr/deal-with-row (last file-b)))

(expect [[0 5 5] [4 9 5] [2 2 5] [3 8 5]] (chr/find-pairs (first file-b) 5))

(expect 9 (chr/exercise02b file-b))
;(expect 277 (chr/exercise02b realFile))

;(expect 2 (Math/sqrt 361527))
;(expect 601 (* 601 601))
;(expect 361527 (- 361527 (* 601 601)))
)
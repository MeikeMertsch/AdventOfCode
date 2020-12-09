(ns christmas.chr20.day03-test
	(:require [christmas.chr20.day03 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))
(comment
(defn getFile [fname]
	(->> (slurp fname)
		 (str/split-lines)
         (map #(re-seq #"[\w|\d]+" %))))

(def filename "resources/chr20/day03real")
(def file (getFile filename))


(expect 477 (chr/day03 filename))
(expect \d (chr/safe-nth 4 "abcdef"))
(expect 686 (chr/day03b filename)) ; 483 too low
)

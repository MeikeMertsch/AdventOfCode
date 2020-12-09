(ns christmas.chr20.day02-test
	(:require [christmas.chr20.day02 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))
(comment
(defn getFile [fname]
	(->> (slurp fname)
		 (str/split-lines)
         (map #(re-seq #"[\w|\d]+" %))))

(def filename "resources/chr20/day02real")
(def file (getFile filename))


(expect 477 (chr/day02 filename))
(expect \d (chr/safe-nth 4 "abcdef"))
(expect 686 (chr/day02b filename)) ; 483 too low
)

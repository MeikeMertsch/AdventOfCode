(ns christmas.chr19.day02-test
	(:require [christmas.chr19.day02 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

  (comment 
    (def realfile "resources/chr19/day02real")
(def file "resources/chr19/day02")

(expect 3500 (chr/day02 (slurp file) 9 10))
(expect 30 (chr/day02 "1,1,1,4,99,5,6,0,99" 1 1))
(expect 6087827 (chr/day02 (slurp realfile) 12 2))

(expect 5379 (chr/day02b (slurp realfile))))
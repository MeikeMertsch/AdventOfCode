(ns christmas.chr15.day13-test
  (:require [christmas.chr15.day13 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day13real"))

(expect ["Alice" "Bob" "Carol" "David" "Eric" "Frank" "George" "Mallory"] (chr/all-guests file))

(expect 664 (chr/day13 file))
(expect 640 (chr/day13b file)) ; runs 45s+
)
(ns christmas.chr16.day18
  (:require [clojure.string :as str]))

(defn trap? [previous-tiles]
   (get {["^" "^" "."] "^" ["." "^" "^"] "^" ["^" "." "."] "^" ["." "." "^"] "^"} previous-tiles "."))

(defn next-row [previous-row]
		 (->> (lazy-cat ["."] previous-row ["."])
		 		   (partition 3 1)
		 		   (map trap?)
		 )
)

(defn tile-room [input room-length]
	  (->> (seq input)
	  					(map str)
	  					(iterate next-row)
	  	    (take room-length)
	  	    flatten
	  	    (filter #(= "." %))
	  	    count
	  ))
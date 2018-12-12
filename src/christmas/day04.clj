(ns christmas.day04
  (:require [christmas.core :refer :all]
            [clojure.string :as str]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(def file04
	(->> (slurp "resources/Input04real")
		 (clojure.string/split-lines)
		 sort
		 (map #(str/split % #"] "))))






(def thing [#{487 475 472 473 491 493 490 478 470 471 479 495 483 485 496 474 477 476 494 489 482 484 492 481 480 486 488} #{410 430 416 415 429 420 418 412 419 417 428 426 413 424 411 431 427 414 421 422 423 409 425}])

;(expect "" (apply min (first (map #(into [] %) (flatten thing)))))
;(expect "" (apply max (second (map #(into [] %) (flatten thing)))))

;[#{487 475 472 473 491 493 490 478 470 471 479 495 483 485 496 474 477 476 494 489 482 484 492 481 480 486 488} #{410 430 416 415 429 420 418 412 419 417 428 426 413 424 411 431 427 414 421 422 423 409 425}]

;(expect 118840 (exercise03))

;(expect 118840 (exercise03b))
(def custom-formatter (f/formatter "[yy-MM-dd HH:mm"))

(defn format-date
  [date-str]
  (f/parse custom-formatter date-str))

(defn minuteOfDate [my_date]
	(t/minute (format-date my_date)))


(defn guardStarts? [the_string]
	(str/starts-with? the_string "Guard"))

(defn guardNumber [the_string]
	(last 
		(str/split 
			(->> (str/split the_string #" ")
				 (filter #(str/starts-with? % "#"))
				 first)		
	        #"\#")))

(defn makeNewGuardFile [thingy the_entry]
	(conj thingy [(guardNumber (last the_entry)) (repeat 60 0)]))

(defn glueTogether [part1 part2]
	(if (nil? part1) 
	  	(vector part2)
	  	(vector part1 part2)
	  )
	)


(defn setSleepy [the_object the_entry]
	(let [guard  (first the_object)
	      schema (last the_object)
	      split-schema (split-at (minuteOfDate (first the_entry)) schema)
	      glued-schema (concat (first split-schema) (map (constantly 1) (last split-schema)))]
	  	(glueTogether guard glued-schema))
	)

(defn setValue [the_object the_entry value]
	(let [guard  (first the_object)
	      schema (last the_object)
	      split-schema (split-at (minuteOfDate (first the_entry)) schema)
	      glued-schema (concat (first split-schema) (map (constantly value) (last split-schema)))]
	  	(glueTogether guard glued-schema))
	)

(defn handleSplitting [thingy the_entry operation value]
	(let [old      (if (nil? (butlast thingy)) []  (into [] (butlast thingy)))
	      the_rest (operation (last thingy) the_entry value)]
	  	(conj old the_rest)
	  	))

(defn guardFallsAsleep? [the_string]
	(str/starts-with? the_string "falls"))

(defn treatEntry [thingy the_entry]
  	(cond
    	(guardStarts? (last the_entry)) (makeNewGuardFile thingy the_entry)
    	(guardFallsAsleep? (last the_entry)) (handleSplitting thingy the_entry setValue 1)
    	:else (handleSplitting thingy the_entry setValue 0)))


(defn sumSleepiness [my_calendar_map]
	(->> (last my_calendar_map)
		 (map last)
		 (map #(apply + %))
		 (apply +)
;	(get (first my_calendar_map) my_calendar_map))
)
)

(def calendars 
	(->> file04
		(reductions #(treatEntry %1 %2) [])
		last
		(group-by first)))

(defn getSleepiestGuard []
	(->> calendars
		(#(zipmap (map first %) (map sumSleepiness %)))
		(into [])
		(sort-by second)
		last
		first
		))

(defn exercise04 []
	(* (int (bigint (getSleepiestGuard)))
	(->> (calendars (getSleepiestGuard))
	     (map last)
	     (apply map +)
	     (map-indexed vector)
	     ;(#(apply max (map last %)))

	     (#(remove (fn [x] (not= (apply max (map last %)) (last x))) %))
	     ffirst
	     )
)
)

(defn sumPerGuard [my_set]
	(->> (last my_set)
	(map last)
	(apply map +)
	(vector (first my_set))
	))

(defn maxPerGuard [[guard calendar]]
	[(apply max calendar) guard]
	)

(def guardSums 
	(->> calendars
		(into [])
		(map sumPerGuard)))

(defn sleepiestMinute [[maximum guard]]
	;(->> (get guard guardSums))
	(->> (filter #(= guard (first %)) guardSums)
		first 
		last
        (map-indexed vector)
        (#(remove (fn [x] (not= maximum (last x))) %))
        ffirst

		)
	)

(defn exercise04b []
	(->> ;(keys calendars)
		guardSums
		(map maxPerGuard)
		sort
		last
		;sleepiestMinute
		(#(* (int (bigint (last %))) (sleepiestMinute %)))
		;last
		;(#(map (->>  
		;last 
		;(map last)
		;(apply map +)
		))
		 ;(zipmap (keys calendars) (vals calendars))

		


(def my_time "[1518-11-17 00:52")
;(expect my_time (format-date my_time))
;(expect 3212 (exercise04))
